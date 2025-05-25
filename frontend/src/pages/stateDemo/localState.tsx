import { useHookstate } from '@hookstate/core';

// Local state

// Set value
// state.a.set(p => p + 1) // increments value of property a
// or
// state['a'].set(p => p + 1)
// or
// state.merge(p => ({ a: p.a + 1 }))
// batch
// state.merge(p => ({ b: p.a, a: p.b }))

// Append value
// const state = useHookstate([1000])
// state[state.length].set(2000)
// or 
// state.merge([2000])  better

//concat value from front 
// const state = useHookstate([3000, 4000])
// state.set(p => {
//     p.splice(0, 0, 1000, 2000);
//     return p;
// })

// remove value
// import { none } from '@hookstate/core'
// const state = useHookstate([1000, 2000, 3000])
// state.merge({
//     0: 2,
//     1: none,
//     3: 4000
// }) // state value will be: [2, 3000, 4000]

function LocalState() {
    const state = useHookstate<Record<string, number>>({a: 0});
    return <>
        <b>Counter value: {state.a.get()} </b>
        <button onClick={() => state.a.set(p => p + 1)}>Increment1</button>
        <button onClick={() => state['a'].set(p => p + 1)}>Increment2</button>
        <button onClick={() => state.merge(p => ({ a: p.a + 1}))}>Increment3</button>
    </>
}

export default LocalState