"use client";

import { useTranslations } from "next-intl";
import {
  Home,
  Users,
  Send,
  BookOpen,
  Bookmark,
  Settings,
  User,
  LogOut,
} from "lucide-react";

const menuItems = [
  { key: "home", icon: Home, href: "/" },
  { key: "clubs", icon: Users, href: "/clubs" },
  { key: "publish", icon: Send, href: "/publish" },
  { key: "requests", icon: BookOpen, href: "/requests" },
  { key: "saved", icon: Bookmark, href: "/saved" },
  { key: "settings", icon: Settings, href: "/settings" },
  { key: "profile", icon: User, href: "/profile" },
  { key: "logout", icon: LogOut, href: "/logout" },
];

export default function Navbar() {
  const t = useTranslations("menu");

  return (
    <nav className="w-full bg-white px-4 py-3 flex justify-center">
      <ul className="flex gap-2 items-start flex-col">
        {menuItems.map((item) => {
          const Icon = item.icon;

          return (
            <li key={item.key}>
              <a
                href={item.href}
                className="flex items-center gap-2 text-gray-700 hover:text-black transition focus:outline-none focus:ring-2 focus:ring-black rounded-md px-2 py-1"
              >
                <Icon size={18} />
                <span className="text-sm">{t(item.key)}</span>
              </a>
            </li>
          );
        })}
      </ul>
    </nav>
  );
}