/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { PayappTestModule } from '../../../test.module';
import { CompanyStateTaxComponent } from '../../../../../../main/webapp/app/entities/company-state-tax/company-state-tax.component';
import { CompanyStateTaxService } from '../../../../../../main/webapp/app/entities/company-state-tax/company-state-tax.service';
import { CompanyStateTax } from '../../../../../../main/webapp/app/entities/company-state-tax/company-state-tax.model';

describe('Component Tests', () => {

    describe('CompanyStateTax Management Component', () => {
        let comp: CompanyStateTaxComponent;
        let fixture: ComponentFixture<CompanyStateTaxComponent>;
        let service: CompanyStateTaxService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [CompanyStateTaxComponent],
                providers: [
                    CompanyStateTaxService
                ]
            })
            .overrideTemplate(CompanyStateTaxComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyStateTaxComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyStateTaxService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new CompanyStateTax(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.companyStateTaxes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
