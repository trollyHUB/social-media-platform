import apiClient from "./client";
import type { AuthResponse, LoginForm, RegisterForm, User } from "@/types";

export const authApi = {
  login: (data: LoginForm) =>
    apiClient.post<AuthResponse>("/auth/login", data).then((r) => r.data),

  register: (data: RegisterForm) =>
    apiClient.post<AuthResponse>("/auth/register", data).then((r) => r.data),

  me: () =>
    apiClient.get<User>("/auth/me").then((r) => r.data),

  logout: () =>
    apiClient.post("/auth/logout"),
};
