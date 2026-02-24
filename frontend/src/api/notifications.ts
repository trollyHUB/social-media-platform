import apiClient from "./client";
import type { Notification } from "@/types";

export const notificationsApi = {
  getAll: () =>
    apiClient.get<Notification[]>("/notifications").then((r) => r.data),

  getUnreadCount: () =>
    apiClient.get<{ count: number }>("/notifications/count").then((r) => r.data.count),

  markAsRead: (id: number) =>
    apiClient.put(`/notifications/${id}/read`),

  markAllAsRead: () =>
    apiClient.put("/notifications/read-all"),
};
