import React, { Component } from "react";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import Header from "../Header/Header";
import Login from "../auth/Login";
import Register from "../auth/Register";
import Dashboard from "../Dashboard/Dashboard";

class App extends Component {
  render() {
    return (
      <Router>
        <Header />
          <div className="container mt-3">
            <Switch>
              <Route exact path={["/", "/dashboard"]} component={Dashboard} />
              <Route exact path="/login" component={Login} />
              <Route exact path="/register" component={Register} />
            </Switch>
          </div>
      </Router>
    );
  }
}

export default App;
