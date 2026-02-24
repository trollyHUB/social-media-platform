import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { usersApi } from "@/api/users";

export const useUsers = () =>
  useQuery({ queryKey: ["users"], queryFn: usersApi.getAll });

export const useUser = (id: number) =>
  useQuery({ queryKey: ["users", id], queryFn: () => usersApi.getById(id), enabled: !!id });

export const useSuggestedUsers = () =>
  useQuery({ queryKey: ["users", "suggested"], queryFn: usersApi.getSuggested });

export const useFollowUser = () => {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (id: number) => usersApi.follow(id),
    onSuccess: () => {
      qc.invalidateQueries({ queryKey: ["users"] });
    },
  });
};

export const useUnfollowUser = () => {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (id: number) => usersApi.unfollow(id),
    onSuccess: () => {
      qc.invalidateQueries({ queryKey: ["users"] });
    },
  });
};
