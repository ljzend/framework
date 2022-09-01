import defaultSettings from '@/settings'

const title = defaultSettings.title || 'Admin Vue FrameWork'

export default function getPageTitle(pageTitle) {
  if (pageTitle) {
    return `${pageTitle} - ${title}`
  }
  return `${title}`
}
