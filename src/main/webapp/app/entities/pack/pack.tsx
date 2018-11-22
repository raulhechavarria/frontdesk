import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { openFile, byteSize, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './pack.reducer';
import { IPack } from 'app/shared/model/pack.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPackProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Pack extends React.Component<IPackProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { packList, match } = this.props;
    return (
      <div>
        <h2 id="pack-heading">
          Packs
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp; Create new Pack
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Name Front Desk Receive</th>
                <th>Name Front Desk Delivery</th>
                <th>Name Pickup</th>
                <th>Date Received</th>
                <th>Date Pickup</th>
                <th>Pixel</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {packList.map((pack, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${pack.id}`} color="link" size="sm">
                      {pack.id}
                    </Button>
                  </td>
                  <td>{pack.name}</td>
                  <td>{pack.nameFrontDeskReceive}</td>
                  <td>{pack.nameFrontDeskDelivery}</td>
                  <td>{pack.namePickup}</td>
                  <td>
                    <TextFormat type="date" value={pack.dateReceived} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={pack.datePickup} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>
                    {pack.pixel ? (
                      <div>
                        <a onClick={openFile(pack.pixelContentType, pack.pixel)}>
                          <img src={`data:${pack.pixelContentType};base64,${pack.pixel}`} style={{ maxHeight: '30px' }} />
                          &nbsp;
                        </a>
                        <span>
                          {pack.pixelContentType}, {byteSize(pack.pixel)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${pack.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${pack.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${pack.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ pack }: IRootState) => ({
  packList: pack.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Pack);
