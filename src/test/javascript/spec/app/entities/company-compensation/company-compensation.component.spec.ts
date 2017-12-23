/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { PayappTestModule } from '../../../test.module';
import { CompanyCompensationComponent } from '../../../../../../main/webapp/app/entities/company-compensation/company-compensation.component';
import { CompanyCompensationService } from '../../../../../../main/webapp/app/entities/company-compensation/company-compensation.service';
import { CompanyCompensation } from '../../../../../../main/webapp/app/entities/company-compensation/company-compensation.model';

describe('Component Tests', () => {

    describe('CompanyCompensation Management Component', () => {
        let comp: CompanyCompensationComponent;
        let fixture: ComponentFixture<CompanyCompensationComponent>;
        let service: CompanyCompensationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [CompanyCompensationComponent],
                providers: [
                    CompanyCompensationService
                ]
            })
            .overrideTemplate(CompanyCompensationComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyCompensationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyCompensationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new CompanyCompensation(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.companyCompensations[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
