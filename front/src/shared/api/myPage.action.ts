"use server";

import AlumniLinkAPI from "../config/AxiosConfig";

export const get_my_posts = async () => {
  try {
    const res = await AlumniLinkAPI.get("/posts/my");
    if (res.status == 200) {
      return res.data;
    }
  } catch (err: any) {
    if (err.status === 404) {
      return 404; // user's token is not valid
    }
  }
};
