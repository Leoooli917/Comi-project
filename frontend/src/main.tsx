
import App from "./pages/App";
// import About from "./pages/About";
// import GlobalState from "./pages/stateDemo/globalState1";
// import GlobalState1 from "./pages/stateDemo/globalState2";
// import LocalState from "./pages/stateDemo/localState";
import "./index.css";
import { createRoot } from "react-dom/client";
import { BrowserRouter } from "react-router-dom";
import "@arco-design/web-react/dist/css/arco.css";

const container = document.getElementById('root');
const root = createRoot(container as HTMLElement);
root.render(
  <BrowserRouter>
    <App />
  </BrowserRouter>
)
