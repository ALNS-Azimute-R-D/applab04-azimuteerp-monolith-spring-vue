export interface IBrand {
  id?: number;
  acronym?: string;
  name?: string;
  description?: string | null;
  logoBrandContentType?: string | null;
  logoBrand?: string | null;
}

export class Brand implements IBrand {
  constructor(
    public id?: number,
    public acronym?: string,
    public name?: string,
    public description?: string | null,
    public logoBrandContentType?: string | null,
    public logoBrand?: string | null,
  ) {}
}
