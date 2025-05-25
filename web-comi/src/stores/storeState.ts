import { hookstate, useHookstate } from '@hookstate/core';
import { devtools } from '@hookstate/devtools';

export const storeState = hookstate<{ isReady: boolean}>({
    isReady: false
}, devtools({ key: 'store' }))