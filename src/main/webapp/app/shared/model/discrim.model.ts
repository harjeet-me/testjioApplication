export interface IDiscrim {
  id?: number;
  name?: string;
}

export class Discrim implements IDiscrim {
  constructor(public id?: number, public name?: string) {}
}
