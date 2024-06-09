import { type ITownCity } from '@/shared/model/town-city.model';

export interface IDistrict {
  id?: number;
  acronym?: string;
  name?: string;
  geoPolygonAreaContentType?: string | null;
  geoPolygonArea?: string | null;
  townCity?: ITownCity;
}

export class District implements IDistrict {
  constructor(
    public id?: number,
    public acronym?: string,
    public name?: string,
    public geoPolygonAreaContentType?: string | null,
    public geoPolygonArea?: string | null,
    public townCity?: ITownCity,
  ) {}
}
