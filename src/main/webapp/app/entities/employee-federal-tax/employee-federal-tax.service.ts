import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { EmployeeFederalTax } from './employee-federal-tax.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class EmployeeFederalTaxService {

    private resourceUrl = SERVER_API_URL + 'api/employee-federal-taxes';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(employeeFederalTax: EmployeeFederalTax): Observable<EmployeeFederalTax> {
        const copy = this.convert(employeeFederalTax);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(employeeFederalTax: EmployeeFederalTax): Observable<EmployeeFederalTax> {
        const copy = this.convert(employeeFederalTax);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<EmployeeFederalTax> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to EmployeeFederalTax.
     */
    private convertItemFromServer(json: any): EmployeeFederalTax {
        const entity: EmployeeFederalTax = Object.assign(new EmployeeFederalTax(), json);
        entity.createdDate = this.dateUtils
            .convertLocalDateFromServer(json.createdDate);
        return entity;
    }

    /**
     * Convert a EmployeeFederalTax to a JSON which can be sent to the server.
     */
    private convert(employeeFederalTax: EmployeeFederalTax): EmployeeFederalTax {
        const copy: EmployeeFederalTax = Object.assign({}, employeeFederalTax);
        copy.createdDate = this.dateUtils
            .convertLocalDateToServer(employeeFederalTax.createdDate);
        return copy;
    }
}
