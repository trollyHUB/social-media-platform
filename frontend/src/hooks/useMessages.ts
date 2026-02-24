import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { messagesApi } from "@/api/messages";
import { useAuthStore } from "@/store/authStore";

export const useChats = () => {
  const isAuth = useAuthStore((s) => s.isAuthenticated);
  return useQuery({
    queryKey: ["chats"],
    queryFn: messagesApi.getChats,
    enabled: isAuth,
  });
};

export const useChatMessages = (userId: number) =>
  useQuery({
    queryKey: ["messages", userId],
    queryFn: () => messagesApi.getMessages(userId),
    enabled: !!userId,
    refetchInterval: 5000,
  });

export const useMessages = useChatMessages; // Алиас — ChatPage использует useMessages

export const useSendMessage = () => {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: ({ recipientId, content }: { recipientId: number; content: string }) =>
      messagesApi.send(recipientId, content),
    onSuccess: (_d, vars) => {
      qc.invalidateQueries({ queryKey: ["messages", vars.recipientId] });
      qc.invalidateQueries({ queryKey: ["chats"] });
    },
  });
};
