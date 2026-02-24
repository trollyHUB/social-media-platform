import apiClient from "./client";
import type { User } from "@/types";

export const usersApi = {
  getAll: () =>
    apiClient.get<User[]>("/users").then((r) => r.data),

  getById: (id: number) =>
    apiClient.get<User>(`/users/${id}`).then((r) => r.data),

  update: (id: number, data: Partial<User>) =>
    apiClient.put<User>(`/users/${id}`, data).then((r) => r.data),

  delete: (id: number) =>
    apiClient.delete(`/users/${id}`),

  follow: (id: number) =>
    apiClient.post(`/users/${id}/follow`),

  unfollow: (id: number) =>
    apiClient.delete(`/users/${id}/follow`),

  getFollowers: (id: number) =>
    apiClient.get<User[]>(`/users/${id}/followers`).then((r) => r.data),

  getFollowing: (id: number) =>
    apiClient.get<User[]>(`/users/${id}/following`).then((r) => r.data),

  getSuggested: () =>
    apiClient.get<User[]>("/users/suggested").then((r) => r.data),
};
