export interface ICustomerType {
  id?: number;
  name?: string;
  description?: string | null;
  businessHandlerClazz?: string | null;
}

export class CustomerType implements ICustomerType {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string | null,
    public businessHandlerClazz?: string | null,
  ) {}
}
