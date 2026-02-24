import apiClient from "./client";
import type { Message, Chat } from "@/types";

export const messagesApi = {
  getChats: () =>
    apiClient.get<Chat[]>("/messages/chats").then((r) => r.data),

  getMessages: (userId: number) =>
    apiClient.get<Message[]>(`/messages/${userId}`).then((r) => r.data),

  send: (recipientId: number, content: string) =>
    apiClient.post<Message>("/messages/send", { recipientId, content }).then((r) => r.data),
};
