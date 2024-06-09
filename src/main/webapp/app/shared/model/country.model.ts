import { type ContinentEnum } from '@/shared/model/enumerations/continent-enum.model';
export interface ICountry {
  id?: number;
  acronym?: string;
  name?: string;
  continent?: keyof typeof ContinentEnum;
  geoPolygonAreaContentType?: string | null;
  geoPolygonArea?: string | null;
}

export class Country implements ICountry {
  constructor(
    public id?: number,
    public acronym?: string,
    public name?: string,
    public continent?: keyof typeof ContinentEnum,
    public geoPolygonAreaContentType?: string | null,
    public geoPolygonArea?: string | null,
  ) {}
}
