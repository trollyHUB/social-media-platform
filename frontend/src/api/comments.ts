import apiClient from "./client";
import type { Comment } from "@/types";

export const commentsApi = {
  getByPost: (postId: number) =>
    apiClient.get<Comment[]>(`/posts/${postId}/comments`).then((r) => r.data),

  create: (postId: number, data: { content: string }) =>
    apiClient.post<Comment>(`/posts/${postId}/comments`, data).then((r) => r.data),

  delete: (id: number) =>
    apiClient.delete(`/comments/${id}`),
};
