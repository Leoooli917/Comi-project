import { hookstate } from '@hookstate/core';
import { devtools } from '@hookstate/devtools';

export const singleState = hookstate<number>(0,  devtools({ key: 'demo1' }))

export const globalState = hookstate<{ nums: number, total?: number}>({
    nums: 0
}, devtools({ key: 'demo' }))
