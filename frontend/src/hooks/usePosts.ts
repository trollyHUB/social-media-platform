import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { postsApi } from "@/api/posts";
import type { CreatePostForm, Post } from "@/types";

export const usePosts = () =>
  useQuery({ queryKey: ["posts"], queryFn: postsApi.getAll, staleTime: 60000 });

export const usePost = (id: number) =>
  useQuery({ queryKey: ["posts", id], queryFn: () => postsApi.getById(id), enabled: !!id });

export const useUserPosts = (userId: number) =>
  useQuery({ queryKey: ["posts", "user", userId], queryFn: () => postsApi.getByUser(userId), enabled: !!userId });

export const useCreatePost = () => {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (data: CreatePostForm) => postsApi.create(data),
    onSuccess: () => qc.invalidateQueries({ queryKey: ["posts"] }),
  });
};

export const useLikePost = () => {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (id: number) => postsApi.like(id),
    onMutate: async (id) => {
      await qc.cancelQueries({ queryKey: ["posts"] });
      const prev = qc.getQueryData<Post[]>(["posts"]);
      qc.setQueryData<Post[]>(["posts"], (old) =>
        old?.map((p) =>
          p.id === id ? { ...p, likes: p.liked ? p.likes - 1 : p.likes + 1, liked: !p.liked } : p
        )
      );
      return { prev };
    },
    onError: (_e, _v, ctx) => {
      if (ctx?.prev) qc.setQueryData(["posts"], ctx.prev);
    },
    onSettled: () => qc.invalidateQueries({ queryKey: ["posts"] }),
  });
};

export const useDeletePost = () => {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (id: number) => postsApi.delete(id),
    onSuccess: () => qc.invalidateQueries({ queryKey: ["posts"] }),
  });
};
