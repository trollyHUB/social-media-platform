import apiClient from "./client";
import type { PlatformStats } from "@/types";

export const statsApi = {
  get: () =>
    apiClient.get<PlatformStats>("/stats").then((r) => r.data),
};
