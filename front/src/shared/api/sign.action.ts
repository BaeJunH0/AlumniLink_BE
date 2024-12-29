"use server";

import AlumniLinkAPI from "@/shared/config/AxiosConfig";

export const get_sign_in = async ({
  nickname,
  password,
}: {
  nickname: string;
  password: string;
}) => {
  try {
    const request = {
      nickname: nickname,
      password: password,
    };
    const res = await AlumniLinkAPI.post("/auth/login", request);
    if (res.status === 200) {
      return res.data;
    } else {
      return false;
    }
  } catch (err: any) {
    if (err.status === 401) {
      return 401; // Not Correct Password
    } else if (err.status === 404) {
      return 404; // No user in USER DB
    }
  }
};

export const get_sign_up = async ({
  nickname,
  password,
}: {
  nickname: string;
  password: string;
}) => {
  try {
    const request = {
      nickname: nickname,
      password: password,
    };
    const res = await AlumniLinkAPI.post("/auth/register", request);
    if (res.status === 200) {
      return true;
    } else {
      return false;
    }
  } catch (err: any) {
    if (err.status === 400) {
      return false;
    }
  }
};

export const get_admin_login = async ({
  nickname,
  password,
}: {
  nickname: string;
  password: string;
}) => {
  try {
    const request = {
      nickname: nickname,
      password: password,
    };
    const res = await AlumniLinkAPI.post("/auth/adminLogin", request);
    if (res.status === 200) {
      return true;
    } else {
      return false;
    }
  } catch (err: any) {
    if (err.status === 400) {
      return false;
    }
  }
};
