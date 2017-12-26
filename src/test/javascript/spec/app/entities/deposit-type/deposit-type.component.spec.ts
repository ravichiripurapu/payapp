/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { PayappTestModule } from '../../../test.module';
import { DepositTypeComponent } from '../../../../../../main/webapp/app/entities/deposit-type/deposit-type.component';
import { DepositTypeService } from '../../../../../../main/webapp/app/entities/deposit-type/deposit-type.service';
import { DepositType } from '../../../../../../main/webapp/app/entities/deposit-type/deposit-type.model';

describe('Component Tests', () => {

    describe('DepositType Management Component', () => {
        let comp: DepositTypeComponent;
        let fixture: ComponentFixture<DepositTypeComponent>;
        let service: DepositTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [DepositTypeComponent],
                providers: [
                    DepositTypeService
                ]
            })
            .overrideTemplate(DepositTypeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DepositTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DepositTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new DepositType(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.depositTypes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
