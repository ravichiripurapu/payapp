/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { PayappTestModule } from '../../../test.module';
import { DeductionSubTypeComponent } from '../../../../../../main/webapp/app/entities/deduction-sub-type/deduction-sub-type.component';
import { DeductionSubTypeService } from '../../../../../../main/webapp/app/entities/deduction-sub-type/deduction-sub-type.service';
import { DeductionSubType } from '../../../../../../main/webapp/app/entities/deduction-sub-type/deduction-sub-type.model';

describe('Component Tests', () => {

    describe('DeductionSubType Management Component', () => {
        let comp: DeductionSubTypeComponent;
        let fixture: ComponentFixture<DeductionSubTypeComponent>;
        let service: DeductionSubTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [DeductionSubTypeComponent],
                providers: [
                    DeductionSubTypeService
                ]
            })
            .overrideTemplate(DeductionSubTypeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DeductionSubTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DeductionSubTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new DeductionSubType(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.deductionSubTypes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
