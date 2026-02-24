import apiClient from "./client";
import type { Post, User } from "@/types";

export interface SearchResults {
  posts: Post[];
  users: User[];
}

export const searchApi = {
  searchAll: (q: string) =>
    apiClient.get<SearchResults>("/search", { params: { q, type: "all" } }).then((r) => r.data),

  searchPosts: (q: string) =>
    apiClient.get<Post[]>("/search/posts", { params: { q } }).then((r) => r.data),

  searchUsers: (q: string) =>
    apiClient.get<User[]>("/search/users", { params: { q } }).then((r) => r.data),
};
