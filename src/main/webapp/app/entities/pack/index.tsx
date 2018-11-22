import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Pack from './pack';
import PackDetail from './pack-detail';
import PackUpdate from './pack-update';
import PackDeleteDialog from './pack-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PackUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PackUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PackDetail} />
      <ErrorBoundaryRoute path={match.url} component={Pack} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={PackDeleteDialog} />
  </>
);

export default Routes;
