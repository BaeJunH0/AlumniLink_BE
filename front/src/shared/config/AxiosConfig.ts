import axios, { HeadersDefaults } from "axios";
import Cookies from "js-cookie";

const PUBLIC_API = process.env.NEXT_APP_PUBLIC_KEY as string;

export const AlumniLinkAPI = axios.create({
  baseURL: PUBLIC_API,
  withCredentials: true,
});

type headers = {
  "Content-Type": string;
  Accept: string;
  Authorization: string;
  [key: string]: string;
};

AlumniLinkAPI.defaults.headers = {
  "Content-Type": "application/json;",
  Accept: "application/json",
} as headers & HeadersDefaults;

let refreshFlag = false;
let failedQueue: any[] = [];

AlumniLinkAPI.interceptors.request.use(
  (config) => {
    const token = Cookies.get("access-token") ?? null;
    if (token && config.headers) {
      config.headers.Authorization = token;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

AlumniLinkAPI.interceptors.response.use(
  (res) => res,
  async (err) => {
    const request = err.config;
    if (err.response?.status === 401 && !request._retry) {
      if (refreshFlag) {
        return new Promise(function (resolve, reject) {
          failedQueue.push({ resolve, reject });
        })
          .then((token) => {
            request.headers["Authorization"] = `Bearer ${token}`;
            return AlumniLinkAPI(request);
          })
          .catch((err) => {
            return Promise.reject(err);
          });
      }
    }
    request._retry = true;
    refreshFlag = true;

    const refreshToken = Cookies.get("refresh-token") ?? null;
    if (!refreshToken) {
      return Promise.reject(err);
    }
  }
);

export default AlumniLinkAPI;
