import AlumniLinkAPI from "@/shared/config/AxiosConfig";

export const post_comment = async ({
  body,
  postId,
}: {
  body: string;
  postId: number;
}) => {
  try {
    const request = {
      body: body,
      postId: postId,
    };
    const res = await AlumniLinkAPI.post("/comment", request);
    if (res.status === 200) {
      return res.data;
    } else {
      return false;
    }
  } catch (err: any) {
    if (err.status === 400) {
      return 400; // not valid token
    } else if (err.status === 404) {
      return 404; // no such post OR not valid token
    }
  }
};

export const delete_comment = async ({ commentId }: { commentId: number }) => {
  try {
    const res = await AlumniLinkAPI.delete(`/comments/${commentId}`);
    if (res.status === 204) {
      return res.data;
    } else {
      return false;
    }
  } catch (err: any) {
    if (err.status === 400) {
      return 400; // invalid Token
    } else if (err.status === 403) {
      return 403; // Not equals to user written this comment.
    } else if (err.status === 404) {
      return 404; // No comment OR is changed Token badly.
    }
  }
};
