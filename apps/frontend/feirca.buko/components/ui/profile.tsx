import { useTranslations } from "next-intl";

export default function Profile(){

    const name = "John Doe";

    const t = useTranslations('messages');

    return(
        <div className="profile bg-gray-500  py-10 w-full rounded-r-4xl flex items-center justify-around flex-col gap-3">
                <div className="profileimg rounded-xl w-20 h-20 bg-gray-300">

                </div>
                <p className="text-sm opacity-70">{t('welcomeBack')}</p>
                <h5 className="font-semibold">{name}</h5>

            </div>
    );
}