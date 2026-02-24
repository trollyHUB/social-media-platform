export interface User {
  id: number;
  username: string;
  email: string;
  bio?: string;
  avatarColor: string;
  avatarUrl?: string;
  role?: "USER" | "MODERATOR" | "ADMIN";
  enabled?: boolean;
  createdAt: string;
  lastActive?: string;
  followersCount?: number;
  followingCount?: number;
  postsCount?: number;
}

export interface Post {
  id: number;
  author: User;
  content: string;
  imageUrl?: string;
  likes: number;
  liked?: boolean;
  commentsCount: number;
  createdAt: string;
}

export interface Comment {
  id: number;
  postId: number;
  author: User;
  content: string;
  createdAt: string;
}

export interface Notification {
  id: number;
  type: NotificationType;
  message: string;
  isRead: boolean;
  createdAt: string;
  senderId?: number;
  sender?: User;
  entityId?: number;
  entityType?: string;
}

export enum NotificationType {
  LIKE = "LIKE",
  COMMENT = "COMMENT",
  FOLLOW = "FOLLOW",
  MENTION = "MENTION",
}

export interface Message {
  id: number;
  senderId: number;
  recipientId: number;
  content: string;
  isRead: boolean;
  createdAt: string;
  sender?: User;
}

export interface Chat {
  userId: number;
  user: User;
  lastMessage: Message;
  unreadCount: number;
}

export interface PlatformStats {
  totalUsers: number;
  totalPosts: number;
  totalLikes: number;
  totalComments: number;
}

export interface AuthResponse {
  token: string;
  user: User;
}

export interface LoginForm {
  email: string;
  password: string;
}

export interface RegisterForm {
  username: string;
  email: string;
  password: string;
  bio?: string;
}

export interface CreatePostForm {
  content: string;
  imageUrl?: string;
}
