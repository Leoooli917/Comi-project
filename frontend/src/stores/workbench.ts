import { hookstate, useHookstate } from '@hookstate/core';
import { devtools } from '@hookstate/devtools';
import { localstored } from '@hookstate/localstored';


const workbenchState = hookstate({
    currentStep: 0,
}, localstored({
    key: 'state-key'
}))

const storyIdState = hookstate({
    storyId: 0,
}, localstored({
    key: 'storyId'
}))

export function useWorkbenchState() {
    const state = useHookstate<{
        currentStep: number,
    }>(workbenchState)

    const storyIdS = useHookstate<{
        storyId: number,
    }>(storyIdState)

    return ({
        get currentStep() {
            return state.currentStep.get()
        },
        setNextStep() {
            state.currentStep.set(p => p + 1)
        },
        setCurrentStep(num: number) {
            state.currentStep.set(num)
        },
        get storyId() {
            return storyIdS.storyId.get()
        },
        setStoryId(storyId: number) {
            storyIdS.storyId.set(storyId)
        }
    })
}