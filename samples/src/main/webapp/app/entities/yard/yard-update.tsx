import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICustomer } from 'app/shared/model/customer.model';
import { getEntities as getCustomers } from 'app/entities/customer/customer.reducer';
import { getEntity, updateEntity, createEntity, reset } from './yard.reducer';
import { IYard } from 'app/shared/model/yard.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IYardUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IYardUpdateState {
  isNew: boolean;
  customerId: string;
}

export class YardUpdate extends React.Component<IYardUpdateProps, IYardUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      customerId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getCustomers();
  }

  saveEntity = (event, errors, values) => {
    values.dateDone = new Date(values.dateDone);

    if (errors.length === 0) {
      const { yardEntity } = this.props;
      const entity = {
        ...yardEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/yard');
  };

  render() {
    const { yardEntity, customers, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="jhipsterSampleApplicationApp.yard.home.createOrEditLabel">Create or edit a Yard</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : yardEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="yard-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="streetandnumberLabel" for="streetandnumber">
                    Streetandnumber
                  </Label>
                  <AvField id="yard-streetandnumber" type="text" name="streetandnumber" />
                </AvGroup>
                <AvGroup>
                  <Label id="cityLabel" for="city">
                    City
                  </Label>
                  <AvField id="yard-city" type="text" name="city" />
                </AvGroup>
                <AvGroup>
                  <Label id="frequenceSummerLabel" for="frequenceSummer">
                    Frequence Summer
                  </Label>
                  <AvField id="yard-frequenceSummer" type="string" className="form-control" name="frequenceSummer" />
                </AvGroup>
                <AvGroup>
                  <Label id="frequenceWinterLabel" for="frequenceWinter">
                    Frequence Winter
                  </Label>
                  <AvField id="yard-frequenceWinter" type="string" className="form-control" name="frequenceWinter" />
                </AvGroup>
                <AvGroup>
                  <Label id="dateDoneLabel" for="dateDone">
                    Date Done
                  </Label>
                  <AvInput
                    id="yard-dateDone"
                    type="datetime-local"
                    className="form-control"
                    name="dateDone"
                    value={isNew ? null : convertDateTimeFromServer(this.props.yardEntity.dateDone)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="customer.id">Customer</Label>
                  <AvInput id="yard-customer" type="select" className="form-control" name="customer.id">
                    <option value="" key="0" />
                    {customers
                      ? customers.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id} + {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/yard" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />&nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />&nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  customers: storeState.customer.entities,
  yardEntity: storeState.yard.entity,
  loading: storeState.yard.loading,
  updating: storeState.yard.updating,
  updateSuccess: storeState.yard.updateSuccess
});

const mapDispatchToProps = {
  getCustomers,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(YardUpdate);
