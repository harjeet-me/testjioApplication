import { IDynamicDataEnvelope } from 'app/shared/model/dynamic-data-envelope.model';
import { IAuditInfo } from 'app/shared/model/audit-info.model';
import { IDiscrim } from 'app/shared/model/discrim.model';
import { IEnvelope } from 'app/shared/model/envelope.model';

export interface IFileSystem {
  id?: number;
  name?: string;
  dataContentType?: string;
  data?: any;
  ext?: boolean;
  url?: string;
  dynamicDataEnvelope?: IDynamicDataEnvelope;
  auditInfo?: IAuditInfo;
  discrim?: IDiscrim;
  envelope?: IEnvelope;
}

export class FileSystem implements IFileSystem {
  constructor(
    public id?: number,
    public name?: string,
    public dataContentType?: string,
    public data?: any,
    public ext?: boolean,
    public url?: string,
    public dynamicDataEnvelope?: IDynamicDataEnvelope,
    public auditInfo?: IAuditInfo,
    public discrim?: IDiscrim,
    public envelope?: IEnvelope
  ) {
    this.ext = this.ext || false;
  }
}
