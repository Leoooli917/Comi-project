import { hookstate, useHookstate } from '@hookstate/core';
import { devtools } from '@hookstate/devtools';


const picCreatorState = hookstate({
    isLoading: false,
}, devtools({ key: 'picCreator' }))



export function usePicCreatorState() {
    const state = useHookstate<{
        isLoading: boolean,
    }>(picCreatorState)

    return ({
        get isLoading() {
            return state.isLoading.get()
        },
        setIsLoading(isLoading: boolean) {
            state.isLoading.set(isLoading)
        }
    })
}