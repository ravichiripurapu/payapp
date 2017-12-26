import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { CompanyStateTax } from './company-state-tax.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CompanyStateTaxService {

    private resourceUrl = SERVER_API_URL + 'api/company-state-taxes';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(companyStateTax: CompanyStateTax): Observable<CompanyStateTax> {
        const copy = this.convert(companyStateTax);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(companyStateTax: CompanyStateTax): Observable<CompanyStateTax> {
        const copy = this.convert(companyStateTax);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<CompanyStateTax> {
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
     * Convert a returned JSON object to CompanyStateTax.
     */
    private convertItemFromServer(json: any): CompanyStateTax {
        const entity: CompanyStateTax = Object.assign(new CompanyStateTax(), json);
        entity.effectiveDate = this.dateUtils
            .convertLocalDateFromServer(json.effectiveDate);
        entity.createdDate = this.dateUtils
            .convertLocalDateFromServer(json.createdDate);
        return entity;
    }

    /**
     * Convert a CompanyStateTax to a JSON which can be sent to the server.
     */
    private convert(companyStateTax: CompanyStateTax): CompanyStateTax {
        const copy: CompanyStateTax = Object.assign({}, companyStateTax);
        copy.effectiveDate = this.dateUtils
            .convertLocalDateToServer(companyStateTax.effectiveDate);
        copy.createdDate = this.dateUtils
            .convertLocalDateToServer(companyStateTax.createdDate);
        return copy;
    }
}
