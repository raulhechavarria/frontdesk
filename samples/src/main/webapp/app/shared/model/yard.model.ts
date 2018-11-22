import { Moment } from 'moment';
import { ICustomer } from 'app/shared/model//customer.model';

export interface IYard {
  id?: number;
  streetandnumber?: string;
  city?: string;
  frequenceSummer?: number;
  frequenceWinter?: number;
  dateDone?: Moment;
  customer?: ICustomer;
}

export const defaultValue: Readonly<IYard> = {};
