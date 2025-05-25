import { useDemoState } from "../../stores/globalState1"

// Globle state

function Demo() {
    const demoState = useDemoState();
    return (<>
        <div style={{
            marginBottom: 30,
            fontSize: '0.8em',
            display: 'flex'
        }}>
            <div id="idEditInline" style={{
                display: "flex",
                flexGrow:2
            }}>
                <div>
                    <input 
                        type="checkbox"
                        style={{ transform: 'scale(1.6)', marginLeft: 20 }}
                        checked={demoState.isEditableInline}
                        onChange={() => demoState.toggleEditableInline()}
                    />
                </div>
                <div style={{ paddingLeft: 10, paddingBottom: 10 }}>
                    edit inline
                </div>
            </div>
            <div id="idUseScopedState" style={{
                display: "flex",
                flexGrow:2
            }}>
                <div>
                    <input 
                        type="checkbox"
                        style={{ transform: 'scale(1.6)', marginLeft: 20 }}
                        checked = { demoState.isScopedUpdateEnabled }
                        onChange={() => demoState.toggleScopedUpdate() }
                    />
                </div>
                <div style={{ paddingLeft: 10, paddingBottom: 10 }}>
                    use scoped state
                </div>
            </div>
            <div id="idHighlightUpdates" style={{
                display: "flex",
                flexGrow:2
            }}>
                <div>
                    <input 
                        type="checkbox"
                        style={{ transform: 'scale(1.6)', marginLeft: 20 }}
                        checked = { demoState.isHighlightUpdateEnabled }
                        onChange={() => demoState.toggleHighlightUpdate()}
                    />
                </div>
                <div style={{ paddingLeft: 10, paddingBottom: 10 }}>
                    highlight updates
                </div>
            </div>
        </div>
    </>)
}

export default Demo