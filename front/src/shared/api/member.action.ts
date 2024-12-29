// member action 은 Admin 전용이므로, Token 이 필요한 작업이라,
// use server not valid.

import AlumniLinkAPI from "@/shared/config/AxiosConfig";

export const delete_member = async ({ id }: { id: number }) => {
  try {
    const res = await AlumniLinkAPI.delete(`/members/${id}`);
    if (res.status === 204) {
      return true;
    } else {
      return false;
    }
  } catch (err: any) {
    if (err.status === 403) {
      return 403; // not authorizerd
    } else if (err.status === 404) {
      return 404; // no user in USER DB
    }
  }
};

export const grant_authority = async ({ id }: { id: number }) => {
  try {
    const res = await AlumniLinkAPI.patch(`/member/${id}`);
    if (res.status === 200) {
      return res.data;
    } else {
      return false;
    }
  } catch (err: any) {
    if (err.status === 403) {
      return 403; // not authorized
    } else if (err.status === 404) {
      return 404; // no user in USER DB
    }
  }
};

export const get_all_members = async () => {
  try {
    const res = await AlumniLinkAPI.get("/members");
    if (res.status === 200) {
      return res.data;
    } else {
      return false;
    }
  } catch (err: any) {
    if (err.status === 403) {
      return 403; // not authorized
    }
  }
};
