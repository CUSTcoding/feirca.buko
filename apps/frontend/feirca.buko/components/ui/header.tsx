import Link from "next/link";
import Input from "./input";
import { useTranslations } from "next-intl";

export default function Header(){
    const t = useTranslations('messages');
    return(
        <header className="w-full px-9 mb-4 h-10 ">
            <div className=" w-full h-full">
                <div className="logo flex items-center justify-between h-full">
                    <Link href="/">Ferica Buku</Link>
                    <div className="flex items-center gap-3">
                        <Input placeholder={t('search')} />
                    </div>
                </div>
            </div>
        </header>
    );
}