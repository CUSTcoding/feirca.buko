import {getRequestConfig} from 'next-intl/server';

const locales = ['en', 'pt'] as const;

export default getRequestConfig(async ({locale}) => {
  const resolvedLocale =
    locale && locales.includes(locale as (typeof locales)[number])
      ? locale
      : 'en';

  return {
    locale: resolvedLocale,
    messages: (await import(`../messages/${resolvedLocale}.json`)).default
  };
});