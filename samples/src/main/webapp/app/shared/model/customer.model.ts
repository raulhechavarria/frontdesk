import { IYard } from 'app/shared/model//yard.model';

export interface ICustomer {
  id?: number;
  name?: string;
  lastName?: string;
  phone?: string;
  iduser?: number;
  yards?: IYard[];
}

export const defaultValue: Readonly<ICustomer> = {};
