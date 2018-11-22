import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Yard from './yard';
import YardDetail from './yard-detail';
import YardUpdate from './yard-update';
import YardDeleteDialog from './yard-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={YardUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={YardUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={YardDetail} />
      <ErrorBoundaryRoute path={match.url} component={Yard} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={YardDeleteDialog} />
  </>
);

export default Routes;
