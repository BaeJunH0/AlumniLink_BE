import { T_Posting } from "@/entity/post/tag";

export type T_SinglePost_Type = {
  id: number;
  nickname: string;
  title: string;
  body: string;
  tag: T_Posting;
  startTime: string;
  modifiedTime: string;
};

export type T_Posting_Type = {
  title: string;
  body: string;
  tag: T_Posting;
};
