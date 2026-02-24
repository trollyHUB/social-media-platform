import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { commentsApi } from "@/api/comments";

export const useComments = (postId: number) =>
  useQuery({
    queryKey: ["comments", postId],
    queryFn: () => commentsApi.getByPost(postId),
    enabled: !!postId,
  });

export const useAddComment = (postId: number) => {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: ({ content }: { content: string }) => commentsApi.create(postId, { content }),
    onSuccess: () => {
      qc.invalidateQueries({ queryKey: ["comments", postId] });
      qc.invalidateQueries({ queryKey: ["posts"] });
    },
  });
};

export const useDeleteComment = () => {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (id: number) => commentsApi.delete(id),
    onSuccess: () => {
      qc.invalidateQueries({ queryKey: ["comments"] });
      qc.invalidateQueries({ queryKey: ["posts"] });
    },
  });
};
