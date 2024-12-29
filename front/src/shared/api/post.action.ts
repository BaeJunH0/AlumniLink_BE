"use server";

import {
  type T_Posting_Type,
  type T_SinglePost_Type,
} from "@/entity/post/post";
import AlumniLinkAPI from "@/shared/config/AxiosConfig";

export const get_post_list = async () => {
  try {
    const res = await AlumniLinkAPI.get("/posts");
    if (res.status === 200) {
      return res.data as T_SinglePost_Type[];
    } else {
      return false;
    }
  } catch (err: any) {
    if (err.status === 400) {
      alert("error");
    }
  }
};

export const get_comment = async ({ id }: { id: number }) => {
  try {
    const res = await AlumniLinkAPI.get("");
  } catch (err: any) {
    if (err.status === 404) {
      return 404; // no post in post list.
    }
  }
};

export const get_single_post = async ({ id }: { id: number }) => {
  try {
    const res = await AlumniLinkAPI.get(`/posts/${id}`);
    if (res.status === 200) {
      return res.data as T_SinglePost_Type;
    } else {
      return false;
    }
  } catch (err: any) {
    if (err.status === 404) {
      return 404; // no posts in post list.
    }
  }
};

export const post_my_posting = async ({ body, tag, title }: T_Posting_Type) => {
  try {
    const request = {
      title: title,
      body: body,
      tag: tag,
    };
    const res = await AlumniLinkAPI.post("/posts", request);
    if (res.status === 200) {
      // Success
      return res.data;
    } else {
      return false;
    }
  } catch (err: any) {
    if (err.status === 404) {
      return 404; // Bearer Token not valid
    } else if (err.status === 500) {
      return 500; // Parameter is not valid;
    }
  }
};

export const delete_my_posting = async ({ id }: { id: number }) => {
  try {
    const res = await AlumniLinkAPI.delete(`/posts/${id}`);
    if (res.status === 200) {
      // Success
      return res.data;
    } else {
      return false;
    }
  } catch (err: any) {
    if (err.status === 404) {
      return 404; // Bearer Token not valid
    } else if (err.status === 500) {
      return 500; // Parameter is not valid;
    }
  }
};
