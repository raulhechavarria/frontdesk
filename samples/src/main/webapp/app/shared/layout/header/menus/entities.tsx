import React from 'react';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from '../header-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name="Entities" id="entity-menu">
    <DropdownItem tag={Link} to="/entity/customer">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Customer
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/yard">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Yard
    </DropdownItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
