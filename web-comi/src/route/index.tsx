import { useRoutes } from 'react-router-dom'

import LocalState from '@/pages/stateDemo/localState'
import GlobalState1 from '@/pages/stateDemo/globalState1'
// import Home from '@/views/Home'

export default function Router() {
  return useRoutes([
    {
      path: '/',
      element: <LocalState />,
    },
    {
      path: '/global',
      element: <GlobalState1 />,
    }
  ])
}