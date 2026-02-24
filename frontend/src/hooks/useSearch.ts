import { useQuery } from "@tanstack/react-query";
import { searchApi } from "@/api/search";

export const useSearch = (query: string) =>
  useQuery({
    queryKey: ["search", query],
    queryFn: () => searchApi.searchAll(query),
    enabled: query.length >= 2,
    staleTime: 30000,
  });
