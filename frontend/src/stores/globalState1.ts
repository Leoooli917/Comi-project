import { hookstate, useHookstate } from '@hookstate/core';
import { devtools } from '@hookstate/devtools';

const demoState = hookstate({
    isEditableInline: true,
    isScopedUpdateEnabled: true,
    isHighlightUpdatesEnabled: true
}, devtools({ key: 'demo' }))

export function useDemoState() {
    const state = useHookstate<{
        isEditableInline: boolean,
        isScopedUpdateEnabled: boolean,
        isHighlightUpdatesEnabled: boolean
    }>(demoState)
     
    return ({
        get isEditableInline() {
            return state.isEditableInline.get()
        },
        toggleEditableInline() {
            state.isEditableInline.set(p => !p)
        },
        get isScopedUpdateEnabled() {
            return state.isScopedUpdateEnabled.get()
        },
        toggleScopedUpdate() {
            state.isScopedUpdateEnabled.set(p => !p)
        },
        get isHighlightUpdateEnabled() {
            return state.isHighlightUpdatesEnabled.get()
        },
        toggleHighlightUpdate() {
            state.isHighlightUpdatesEnabled.set(p => !p)
        }
    })
}