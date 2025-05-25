import { useHookstate } from '@hookstate/core';
import { globalState, singleState } from "../../stores/globalState2"

// Globle state

function Demo() {
    const global = useHookstate(globalState);
    const single = useHookstate(singleState);
    return <>
        <b>current nums: { single.get() }</b>
        <button onClick={() => singleState.set( p => p + 1)}>Increment</button>
        <br/>
        <b>current nums: { global.nums.get() }</b>
        <button onClick={() => globalState.nums.set( p => p + 1)}>Increment</button>
    </>
}

export default Demo