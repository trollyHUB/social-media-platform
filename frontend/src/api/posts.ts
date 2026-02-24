import apiClient from "./client";
import type { Post, CreatePostForm } from "@/types";

export const postsApi = {
  getAll: () =>
    apiClient.get<Post[]>("/posts").then((r) => r.data),

  getById: (id: number) =>
    apiClient.get<Post>(`/posts/${id}`).then((r) => r.data),

  getByUser: (userId: number) =>
    apiClient.get<Post[]>(`/users/${userId}/posts`).then((r) => r.data),

  create: (data: CreatePostForm) =>
    apiClient.post<Post>("/posts", data).then((r) => r.data),

  update: (id: number, content: string) =>
    apiClient.put<Post>(`/posts/${id}`, { content }).then((r) => r.data),

  delete: (id: number) =>
    apiClient.delete(`/posts/${id}`),

  like: (id: number) =>
    apiClient.post<Post>(`/posts/${id}/like`).then((r) => r.data),
};
